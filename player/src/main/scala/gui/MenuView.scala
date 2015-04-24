import java.net.InetAddress

import module.SongModel

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.effect.DropShadow
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{Stops, LinearGradient}
import scalafx.scene.text.{FontWeight, Font, Text}
import scalafx.scene.{Scene, Node}
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, HBox, StackPane, VBox}
import scalafx.stage.{FileChooser, Popup}

/**
 * Created by yangwu on 4/5/15.
 */

class MenuView() {

  val hostTag = new Text {
    text = "HOST IP"
    font = Font.font("SansSerif", FontWeight.BOLD, 20)
    style = "-fx-font-size: 10pt"
    fill = new LinearGradient(
      endX = 0,
      stops = Stops(Cyan, DodgerBlue)
    )
    effect = new DropShadow {
      color = DodgerBlue
      radius = 25
      spread = 0.25
    }
  }

  val portTag = new Text {
    text = "PORT"
    font = Font.font("SansSerif", FontWeight.BOLD, 20)
    style = "-fx-font-size: 10pt"
    fill = new LinearGradient(
      endX = 0,
      stops = Stops(Cyan, DodgerBlue)
    )
    effect = new DropShadow {
      color = DodgerBlue
      radius = 25
      spread = 0.25
    }
  }

  val _pane = new BorderPane() {

      val hostIp = new TextField { prefColumnCount = 15 }
      val hostPort = new TextField { prefColumnCount = 4 }

      padding = Insets(15)

      top = new Label { prefHeight = 100 }

      center = new HBox {
        children = List(
          hostTag,
          new Label {
            prefWidth = 10
          },
          hostIp,
          new Label { prefWidth = 20 },
          portTag,
          new Label { prefWidth = 10 },
          hostPort
        )
      }

      bottom = new Button("GO") {
        onAction = {
          e: ActionEvent =>
            println("local: "+hostIp.text())
            println("host: "+hostPort.text())
            val prim = new PrimaryView(hostIp.text(), hostPort.text())
            println("----------   -----------")
            Main.stage.scene = new Scene(prim.root, 500, 300) {
              val stylesheet = getClass.getResource("media.css")
              stylesheets.add(stylesheet.toString)
            }
        }
        alignmentInParent = Pos.BOTTOM_LEFT
        margin = Insets(10, 0, 10, 0)
      }

    }

  def pane = _pane

}