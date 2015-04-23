import java.io.File
import java.net.InetAddress

import module.SongModel

import scalafx.Includes._
import scalafx.application.Platform
import scalafx.beans.property.ReadOnlyObjectProperty
import scalafx.event.ActionEvent
import scalafx.event.subscriptions.Subscription
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Node
import scalafx.scene.control.{Label, Button}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, StackPane, GridPane, HBox}
import scalafx.scene.media.MediaPlayer
import scalafx.scene.media.MediaPlayer.Status
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.stage.{Popup, FileChooser}

/**
 * Created by yangwu on 4/4/15.
 */
class PlayerControlsView(songModel: SongModel, ip:String, port:String) extends AbstractView(songModel) {
  private var pauseImg: Image = _
  private var playImg: Image = _
  private var playPauseIcon: ImageView = _
  /** 'subscription' keep track of assigned listeners */
  private var statusInvalidationSubscription: Subscription = _
  private var currentTimeSubscription: Subscription = _
  private var controlPanel: Node = _

  private var config = new ClusterConfig(ip, port)

  songModel.mediaPlayer.onChange(
    (_, oldValue, newValue) => {
      if (oldValue != null)
          removeListenersAndBinding(oldValue)
      addListenersAndBindings(newValue)
    }
  )

  addListenersAndBindings(songModel.mediaPlayer())

  protected def initView(): Node = {
    val likeButton = createLikeButton()
    val hateButton = createHateButton()

    controlPanel = createControlPanel()

    val gp = new GridPane {
      hgap = 1
      vgap = 1
      padding = Insets(10)
    }
    // columnIndex: Int, rowIndex: Int, colspan: Int, rowspan: Int
    gp.add(controlPanel, 4, 0, 1, 2)
    gp.add(likeButton, 5, 0, 1, 2)
    gp.add(hateButton, 6, 0, 1, 2)

    gp
  }

  private def createLikeButton() = new Button {
    id = "likeButton"
    graphic = new ImageView {
      image = new Image(this.getClass.getResourceAsStream("/up.png"), 30, 30, false, true)
    }
    // TODO: spread out votes
    onAction = (ae: ActionEvent) => {
      println("- CAPTURE LIKE -")
    }
  }

  private def createHateButton() = new Button {
    id = "hateButton"
    graphic = new ImageView {
      image = new Image(this.getClass.getResourceAsStream("/down.png"), 30, 30, false, true)
    }
    // TODO: spread out votes
    onAction = (ae: ActionEvent) => {
      println("- CAPTURE DISLIKE -")
      val popWindow = createAlertPopup("ahahahah")
      popWindow.show(Main.stage,
        (Main.stage.width() - popWindow.width()) / 2.0 + Main.stage.x(),
        (Main.stage.height() - popWindow.height()) / 2.0 + Main.stage.y())
    }
  }


  private def createControlPanel(): Node = {
    val playPauseButton = createPlayPauseButton()


    new HBox {
      alignment = Pos.CENTER
      fillHeight = false
      content =  playPauseButton
    }
  }


  private def createPlayPauseButton(): Button = {
    val pauseUrl = getClass.getResource("/pause.png")
    pauseImg = new Image(pauseUrl.toString)

    val playUrl = getClass.getResource("/play.png")
    playImg = new Image(playUrl.toString)

    playPauseIcon = new ImageView {
      image = playImg
    }

    new Button {
      graphic = playPauseIcon
      id = "playPauseButton"
      onAction = (ae: ActionEvent) => {
        val mediaPlayer = songModel.mediaPlayer()
        mediaPlayer.status() match {
          case Status.PLAYING.delegate => mediaPlayer.pause()
          case _ =>
            println(" - BROADCASTING: " + MusicName.name)
            config.broadcast(MusicName.name)  /** where it starts **/
        }
      }
    }

  }

  private def updateStatus(newStatus: Status) {
    if (Status.UNKNOWN == newStatus || newStatus == null) {
      controlPanel.disable = true
    } else {
      controlPanel.disable = false
      playPauseIcon.image = if (Status.PLAYING == newStatus) pauseImg else playImg
    }
  }


  private def addListenersAndBindings(mp: MediaPlayer) {
    statusInvalidationSubscription = mp.status.onInvalidate {
      Platform.runLater {
        updateStatus(songModel.mediaPlayer().status())
      }
    }

    mp.onEndOfMedia = songModel.mediaPlayer().stop()

  }

  private def removeListenersAndBinding(mp: MediaPlayer) {
    statusInvalidationSubscription.cancel()
  }

  def playMusic(music:String): Unit = {
    println("checkpoint")
    val url:String = new File("./"+music+".mp3").toURI().toString()
    println("URL: "+url)
    songModel.url = url
    songModel.mediaPlayer().play()
  }

  private def createAlertPopup(popupText: String) = new Popup {
    inner =>
    content.add(new StackPane {
      children = List(
        new Rectangle {
          width = 300
          height = 200
          arcWidth = 15
          arcHeight = 15
          fill = Color.Black
          stroke = Color.Gray
          strokeWidth = 2
        },
        new BorderPane {
          center = new Label {
            text = "Proposed Song: "+popupText
            wrapText = true
            maxWidth = 280
            maxHeight = 140
          }
          bottom = new HBox {
            maxWidth = 280
            maxHeight = 140
            children = List(
              new Button("OK") {
                onAction = { e: ActionEvent => inner.hide() }
                alignmentInParent = Pos.BOTTOM_LEFT
                margin = Insets(10, 0, 10, 0)
              },
              new Button("PASS") {
                onAction = {e: ActionEvent => inner.hide()}
                alignmentInParent = Pos.BOTTOM_RIGHT
                margin = Insets(10, 0, 10, 0)
              }
            )
          }
        }
      )
    }.delegate
    )
  }
}
