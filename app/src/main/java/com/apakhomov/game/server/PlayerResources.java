package com.apakhomov.game.server;

import java.io.BufferedReader;
import java.io.PrintWriter;

public record PlayerResources(BufferedReader reader, PrintWriter writer) {
}
