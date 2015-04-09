package gui

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.effect.DropShadow
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout._
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.{Font, FontWeight, Text}

/**
 * Created by yangwu on 3/14/15.
 */

object Hello extends JFXApp {

  val echo = new Text {
    text = "Echo"
    font = Font.font("SansSerif", FontWeight.Bold, 20)
    style = "-fx-font-size: 50pt"
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

  val like = new ImageView {
    image = new Image(this.getClass.getResourceAsStream("/up.png"), 30, 30, false, true)
  }

  val dislike = new ImageView {
    image = new Image(this.getClass.getResourceAsStream("/down.png"), 30, 30, false, true)
  }

  val stop = new ImageView {
    image = new Image(this.getClass.getResourceAsStream("/stop.png"), 30, 30, false, true)
  }

  val header = new VBox {
    vgrow = Priority.Always
    hgrow = Priority.Always
    children = new ToolBar {
      prefHeight = 76
      maxHeight = 76
      id = "mainToolBar"
      content = List(
        new Region {
          minWidth = 50
        },
        echo,
        new Region {
          minWidth = 500
        },
        new Button("CREATE") {
          minWidth = 120
          minHeight = 50
          id = "createButton"
        },
        new Button("JOIN") {
          minWidth = 120
          minHeight = 50
          id = "joinButton"
        })
    }
  }

  val localFile = new TreeView[String] {
    minWidth = 200
    minHeight = 200
    showRoot = true
    id = "list"
    root = new TreeItem [String]("Root Node") {
        id = "list"
        expanded = true
        children = ObservableBuffer(
          new TreeItem[String] {
            value = "Node 1"
          },
          new TreeItem[String] {
            value = "Node 2"
          },
          new TreeItem[String] {
            value = "Node 3"
            children = ObservableBuffer(
              (4 to 12).map(n => new TreeItem[String]("Child Node " + n))
            )
          }
        )
    }
  }

  val controlBox = new HBox() {
    prefHeight = 80
    maxHeight = 80
    children =  List(
      new Region {
        minWidth = 600
      },
      new Button() {
        id = "like"
        graphic = like
      },
      new Region {
        minWidth = 10
      },
      new Button() {
        id = "dislike"
        graphic = dislike
      },
      new Region {
        minWidth = 10
      },
      new Button() {
        id = "stop"
        graphic = stop
      }
    )
  }

  val meta = new VBox() {
    id = "meta"
    children = List (
      new Label("  PLAYING GRAPH...") {
        id = "play"
        minHeight = 60
      },
      controlBox,
      new BorderPane {
        prefHeight = 400
        id = "display"
        center = new Label("OTHER INFO. GOES HERE...") {
          id = "play"
        }
        bottom = new Label("  IP Addr: 127.0.0.1    Host IP: 127.0.0.1") {
          id = "ip"
        }
      }
    )
  }

  lazy val splitPane = new SplitPane {
    dividerPositions = 0
    id = "page-splitpane"
    items.addAll(scroll, meta)
  }

  val scroll = new ScrollPane {
    minWidth = 200
    maxWidth = 200
    fitToWidth = true
    fitToHeight = true
    id = "list"
    content = localFile
  }

  /**
   * main stage
   */
  stage = new PrimaryStage {
    title = "ECHO - Connecting Your Music"
    scene = new Scene(1000, 600) {
      stylesheets add "/style.css"
      root = new BorderPane {
        top = header
        center = splitPane
      }
    }
  }
}