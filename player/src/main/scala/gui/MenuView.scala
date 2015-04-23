import java.net.InetAddress

import module.SongModel

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.{Scene, Node}
import scalafx.scene.control._
import scalafx.scene.layout.{HBox, StackPane, VBox}
import scalafx.stage.{FileChooser, Popup}

/**
 * Created by yangwu on 4/5/15.
 */

class MenuView() {

  val _pane = new StackPane {

      val hostIp = new TextField {
        prefColumnCount = 15
      }
      val hostPort = new TextField {
        prefColumnCount = 4
      }

      padding = Insets(15)

      children = List(
        new HBox {
          children = List(
            new Label { // create space
              prefHeight = 20
            },
            new HBox {
              children = List(
                new Label {
                  text = "host ip"
                  wrapText = true
                },
                hostIp,
                new Label {
                  text = "post"
                  wrapText = true
                },
                hostPort
              )
            }
          )
        },
        new Button("GO") {
          onAction = {
            e: ActionEvent =>
              println("local: "+hostIp.text())
              println("host: "+hostPort.text())
              val prim = new PrimaryView(hostIp.text(), hostPort.text())
//              if (hostIp.text()!=InetAddress.getLocalHost.getHostAddress) {
//                val config = new ClusterConfig(hostIp.text(), hostPort.text())
//                Main.playerControlsView.setConfig(config)
//              }
              println("----------   -----------")
              Main.stage.scene = new Scene(prim.root, 500, 300) {
                val stylesheet = getClass.getResource("media.css")
                stylesheets.add(stylesheet.toString)
              }
          }
          alignmentInParent = Pos.BOTTOM_LEFT
          margin = Insets(10, 0, 10, 0)
        }
      )
      prefHeight = 200
      prefWidth = 300
    }

  def pane = _pane
}