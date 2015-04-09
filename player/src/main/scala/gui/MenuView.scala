import module.SongModel

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.Node
import scalafx.scene.control.{Menu, MenuBar, MenuItem}
import scalafx.scene.media.MediaPlayer
import scalafx.stage.FileChooser

/**
 * Created by yangwu on 4/5/15.
 */

class MenuView(songModel: SongModel) extends AbstractView(songModel) {

  def initView(): Node = {
    val open = createOpenMenu()

    new MenuBar() {
      useSystemMenuBar = true
      menus = List(
        new Menu("root") {
          items = List(
            new MenuItem("Create"),
            new MenuItem("Join")
          )
        },
        open
      )
    }
  }

  private def createOpenMenu() = new Menu("open") {
    items = List (
      new MenuItem ("music source") {
        onAction ={
          ae: ActionEvent => {
            println(ae.eventType + " occurred on MenuItem open")

            val fc = new FileChooser() {
              title = "share your music"
            }

            val song = fc.showOpenDialog(viewNode.scene().window())

            if (song != null) {
              songModel.url = song.toURI.toString
              songModel.mediaPlayer().play()
            }
          }
        }
      }
    )
  }

}