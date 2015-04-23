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

  val menu = new MenuView()

  stage = new PrimaryStage {
    title = "Echo - Connecting Your Music"
    scene = new Scene(menu.pane, 500, 300) {
      val stylesheet = getClass.getResource("media.css")
      stylesheets.add(stylesheet.toString)
    }
  }
}