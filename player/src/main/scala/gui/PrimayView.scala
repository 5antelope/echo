import java.io.File

import module.SongModel

import scalafx.scene.control.Label
import scalafx.scene.layout.{VBox, BorderPane, Priority, HBox}

/**
 * Created by yangwu on 4/23/15.
 */

class PrimaryView(ip:String, port:String) {
  private final val songModel = new SongModel() {
    url = new File("./Rolling In The Deep.mp3").toURI().toString()
    print("URL: ")
    println(url)
  }

  SongModel.url = new File("./Rolling In The Deep.mp3").toURI().toString()


  private val titleView = new TitleView(songModel)
  private val metaDataView = new MetadataView(songModel)
  private val playListView = new PlayListView(songModel)
  //  private val menu = new MenuView(songModel)

  val playerControlsView = new PlayerControlsView(songModel, ip, port)

  private val hostIP: String = "128.237.176.210"
  private val localIP: String = "128.237.176.219"


  val _root = new HBox {
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

  def root = _root
}