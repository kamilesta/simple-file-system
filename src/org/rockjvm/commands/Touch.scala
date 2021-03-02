package org.rockjvm.commands
import org.rockjvm.files.{DirEntry, File}
import org.rockjvm.filesystem.State

class Touch(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry =
    File.empty(state.workingDir.path, name)
}
