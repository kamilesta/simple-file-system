package org.rockjvm.commands
import org.rockjvm.files.DirEntry
import org.rockjvm.filesystem.State

class Ls extends Command {

  def createOutput(contents: List[DirEntry]): String = {
    if (contents.isEmpty) ""
    else {
      val entry = contents.head
      entry.name + "[" + entry.getType + "]" + "\n" + createOutput(contents.tail)
    }
  }

  override def apply(state: State): State = {
    val contents = state.workingDir.contents

    val output: String = createOutput(contents)
    state.setMessage(output)
  }
}
