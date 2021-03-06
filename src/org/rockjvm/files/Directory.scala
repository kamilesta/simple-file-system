package org.rockjvm.files

import org.rockjvm.filesystem.FilesystemException

import scala.annotation.tailrec

class Directory(override val parentPath: String,
                override val name: String,
                val contents: List[DirEntry])
  extends DirEntry(parentPath, name) {
  def replaceEntry(entryName: String, newEntry: DirEntry): Directory =
    new Directory(parentPath, entryName, contents.filter(e => !e.name.equals(entryName)) :+ newEntry)

  def findEntry(entryName: String): DirEntry = {
    @tailrec
    def findEntryHelper(name: String, contentList: List[DirEntry]): DirEntry = {
      if (contentList.isEmpty) null
      else if (contentList.head.name.equals(name)) contentList.head
      else findEntryHelper(name, contentList.tail)
    }

    findEntryHelper(entryName, contents)
  }

  def addEntry(newEntry: DirEntry): Directory = new Directory(parentPath, name, contents :+ newEntry)

  def hasEntry(name: String): Boolean = findEntry(name) != null

  def getAllFoldersInPath: List[String] =
    path.substring(1).split(Directory.SEPARATOR).toList.filter(dir => dir.nonEmpty)

  @tailrec
  final def findDescendant(path: List[String]): Directory =
    if (path.isEmpty) this
    else findEntry(path.head).asDirectory.findDescendant(path.tail)

  def asDirectory: Directory = this

  override def asFile: File = throw new FilesystemException("Cannot be converted to file")
}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def empty(parentPath: String, name: String): Directory =
    new Directory(parentPath, name, List())

  def ROOT: Directory = Directory.empty("", "")
}
