import java.io.File

import module.SongModel

import scala.collection._
import scalafx.application.Platform
import scalafx.scene.media.MediaPlayer
import scalafx.scene.media.MediaPlayer.Status

/**
 * Created by yangwu on 4/10/15.
 */

case class PlayList() {

  var arr = mutable.Buffer("music.mp3", "odd.mp3")

  def select(name:String): Unit = {
//    val idx = arr.indexOf(name)
//    if (idx>0) {
//      val tmp = arr(0)
//      arr.update(0, name)
//      arr.update(idx, tmp)
//    }
    println("here here here")
    /** set music name **/
    MusicName.name = arr(0)
    println(MusicName.name)
  }

}