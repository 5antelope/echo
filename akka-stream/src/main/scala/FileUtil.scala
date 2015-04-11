import java.nio.{ByteBuffer, MappedByteBuffer}
import java.nio.channels.FileChannel
import java.nio.file.{StandardOpenOption, Path}

import akka.util.ByteString

/**
 * Created by yangwu on 4/8/15.
 */

object FileUtils {
  /**
   *  Returns a memory mapped byte buffer for the given path. All java.nio
   */
  def mmap(path: Path): MappedByteBuffer = {
    val channel = FileChannel.open(path, StandardOpenOption.READ)
    val result = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size())
    channel.close()
    result
  }

  /**
   *  generates an Iterator[ByteString] from a file
   */
  class ByteBufferIterator (buffer: ByteBuffer, chunkSize: Int) extends Iterator[ByteString] {
    require(buffer.isReadOnly)
    require(chunkSize > 0)

    override def hasNext = buffer.hasRemaining

    override def next(): ByteString = {
      val size = chunkSize min buffer.remaining()
      val temp = buffer.slice()
      temp.limit(size)
      buffer.position(buffer.position() + size)
      ByteString(temp)
    }
  }

}
