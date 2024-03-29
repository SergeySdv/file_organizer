package tasks

import java.nio.file.{Files, Path}

import org.scalatest.{BeforeAndAfterEach, FunSuite}
import parse.VideoFileNameParser

class RenameTaskTest extends FunSuite with BeforeAndAfterEach {
  private val task: RenameTask = new RenameTask(VideoFileNameParser)
  private var path: Path = _

  override def beforeEach() {
    path = Files.createTempFile("Bishoujo_Java_-_Tsumi_to_batsu_no_shoujo_[E12D394A] ", ".avi")
  }

  test("Simple rename") {
    val number = path.toFile.getName.split(" ").takeRight(1).apply(0)
    val renamedPath = task.execute(path)
    val renamed = renamedPath.toFile.getName
    val expected = s"Bishoujo Java Tsumi to batsu no shoujo - $number"
    assertResult(expected)(renamed)
  }

  test("Renamed file exists") {
    val renamedPath = task.execute(path)
    val renamedExisted = task.execute(renamedPath)
    assertResult(renamedPath)(renamedExisted)
  }

  override def afterEach() {
    Files.deleteIfExists(path)
  }
}
