package org.rockjvm.commands
import org.rockjvm.files.{DirEntry, Directory}
import org.rockjvm.filesystem.State

class MkDir(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry =
    Directory.empty(state.workingDir.path, name)
}
