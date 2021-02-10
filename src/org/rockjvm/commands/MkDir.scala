package org.rockjvm.commands

import org.rockjvm.files.{DirEntry, Directory}
import org.rockjvm.filesystem.State

class MkDir(name: String) extends Command {

  override def apply(state: State): State = {
    val workingDir = state.workingDir
    if (workingDir.hasEntry(name)) {
      state.setMessage(s"Entry $name already exists")
    } else if (name.contains(Directory.SEPARATOR)) {
      state.setMessage(s"$name must not contain separators")
    } else if (checkIllegal(name)) {
      state.setMessage(s"$name: illegal entry name")
    } else doMkDir(state, name)
  }

  def checkIllegal(name: String): Boolean = name.contains(".")

  def doMkDir(currentState: State, name: String): State = {
    val workingDir = currentState.workingDir

    val allDirsInPath = workingDir.getAllFoldersInPath
    val newDir = Directory.empty(workingDir.path, name)
    val newRoot = updateStructure(currentState.root, allDirsInPath, newDir)
    val newWorkingDir = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWorkingDir)
  }

  def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
    if (path.isEmpty) currentDirectory.addEntry(newEntry)
    else {
      val oldEntry = currentDirectory.findEntry(path.head).asDirectory
      currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
    }
  }


}
