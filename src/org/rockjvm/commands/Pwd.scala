package org.rockjvm.commands
import org.rockjvm.filesystem.State

class Pwd extends Command {
  override def apply(state: State): State =
    state.setMessage(state.workingDir.path)
}
