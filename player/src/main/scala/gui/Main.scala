import java.io.File

import com.sun.javafx.runtime.VersionInfo
import module.SongModel

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.input.{TransferMode, DragEvent}
import scalafx.scene.layout.{BorderPane, HBox, Priority, VBox}
import scalafx.stage.Popup

object Main extends JFXApp {

  println("JavaFX version: " + VersionInfo.getRuntimeVersion)

  private final val songModel = new SongModel() {
    //url = "http://traffic.libsyn.com/dickwall/JavaPosse373.mp3"
    url = new File("./Rolling In The Deep.mp3").toURI().toString()
    print("URL: ")
    println(url)
  }

  SongModel.url = new File("./Rolling In The Deep.mp3").toURI().toString()


  private val titleView = new TitleView(songModel)
  private val metaDataView = new MetadataView(songModel)
  private val playListView = new PlayListView(songModel)
//  private val menu = new MenuView(songModel)

  val playerControlsView = new PlayerControlsView(songModel)

  private val hostIP: String = "128.237.176.210"
  private val localIP: String = "128.237.176.219"


  val root = new HBox {
    vgrow = Priority.ALWAYS
    hgrow = Priority.ALWAYS
    children = List (
      new BorderPane {
//        top = new VBox() {
//          children = List(menu.viewNode, titleView.viewNode)
//        }
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

  val menu = new MenuView()

  stage = new PrimaryStage {
    title = "Echo - Connecting Your Music"
    scene = new Scene(menu.pane, 500, 300) {
      val stylesheet = getClass.getResource("media.css")
      stylesheets.add(stylesheet.toString)
    }
  }
}