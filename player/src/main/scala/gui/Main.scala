import java.io.File

import com.sun.javafx.runtime.VersionInfo
import module.SongModel

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.input.{TransferMode, DragEvent}
import scalafx.scene.layout.{BorderPane, HBox, Priority, VBox}

object Main extends JFXApp {

  println("JavaFX version: " + VersionInfo.getRuntimeVersion)

  private final val songModel = new SongModel() {
    //url = "http://traffic.libsyn.com/dickwall/JavaPosse373.mp3"
    url = new File("./music.mp3").toURI().toString()
    print("URL: ")
    println(url)
  }

  SongModel.url = new File("./music.mp3").toURI().toString()

//  ClusterConfig().setUp()

  val conf = ClusterConfig(songModel)
  conf.setUp()

  private val titleView = new TitleView(songModel)
  private val metaDataView = new MetadataView(songModel)
  private val playListView = new PlayListView(songModel)
  private val playerControlsView = new PlayerControlsView(songModel, conf)
  private val menu = new MenuView(songModel)

  private val hostIP: String = "127.0.0.1"
  private val localIP: String = "127.0.0.1"

  val root = new HBox {
    vgrow = Priority.ALWAYS
    hgrow = Priority.ALWAYS
    children = List (
      new BorderPane {
        top = new VBox() {
          children = List(menu.viewNode, titleView.viewNode)
        }
        center = new VBox() {
          children =
            metaDataView.viewNode
        }
        bottom = new VBox() {
          children = List(playerControlsView.viewNode,
            new Label(" Host IP: " + hostIP + "  Local IP: " + localIP) {
              id = "ip"
            }
          )
        }
        maxWidth = 350
      },
      new VBox() {
        children = playListView.viewNode
      }
    )
  }

  stage = new PrimaryStage {
    title = "Echo - Connecting Your Music"
    scene = new Scene(root, 500, 300) {
      val stylesheet = getClass.getResource("media.css")
      stylesheets.add(stylesheet.toString)
    }
  }


}