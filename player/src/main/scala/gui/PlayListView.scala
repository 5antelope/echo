import module.SongModel

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Orientation
import scalafx.scene.Node
import scalafx.scene.control.ListView

/**
 * Created by yangwu on 4/5/15.
 */

class PlayListView(songModel: SongModel) extends AbstractView(songModel) {
  /**
   * need actual play list here
   */
  def initView(): Node = {
    val seq = Seq("Stay With Me","Lay Me Down","I'm Not the Only One","Money On My Mind")

    new ListView[String] {
      id = "playList"
      items = ObservableBuffer(seq)
      orientation = Orientation.VERTICAL
      maxWidth = 150
    }
  }
}