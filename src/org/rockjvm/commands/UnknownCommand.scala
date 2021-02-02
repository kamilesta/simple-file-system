package org.rockjvm.commands
import org.rockjvm.filesystem.State

class UnknownCommand extends Command {
  override def apply(state: State): State =
    state.setMessage("Command not found")
}
