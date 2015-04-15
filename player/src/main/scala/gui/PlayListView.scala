import javafx.event.EventHandler

import module.SongModel

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Orientation
import scalafx.scene.Node
import scalafx.scene.control.ListView
import scalafx.scene.input.MouseEvent

/**
 * Created by yangwu on 4/5/15.
 */

class PlayListView(songModel: SongModel) extends AbstractView(songModel) {
  /**
   * need actual play list here
   */
  def initView(): Node = {
    val seq = Seq("Rolling In The Deep","Heartbreaker")

    val l = new ListView[String] {
      id = "playList"
      items = ObservableBuffer(seq)
      orientation = Orientation.VERTICAL
      maxWidth = 150

      selectionModel().selectedItem.onChange {
        (_, _, newValue) => {
          val p = new PlayList
          p.select(newValue)
          println("- CUR. SRC:"+MusicName.name)
          println("SELECT: "+newValue)
        }
      }

    }

    l;
  }
}