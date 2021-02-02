package org.rockjvm.filesystem

import org.rockjvm.commands.Command
import org.rockjvm.files.Directory

import java.util.Scanner

object Filesystem extends App {

  val root = Directory.ROOT
  var state = State(root, root)
  val scanner = new Scanner(System.in)

  while(true) {
    state.show()
    state = Command.from(scanner.nextLine())(state)
  }
}
