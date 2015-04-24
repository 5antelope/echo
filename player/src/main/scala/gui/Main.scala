import java.io.File

import com.sun.javafx.runtime.VersionInfo
import module.SongModel

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.input.{TransferMode, DragEvent}
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.stage.Popup

object Main extends JFXApp {

  println("JavaFX version: " + VersionInfo.getRuntimeVersion)

  val menu = new MenuView()

  var playList = new PlayListView(null)

  stage = new PrimaryStage {
    title = "Echo - Connecting Your Music"
    scene = new Scene(menu.pane, 500, 300) {
      val stylesheet = getClass.getResource("media.css")
      stylesheets.add(stylesheet.toString)
    }
  }
}