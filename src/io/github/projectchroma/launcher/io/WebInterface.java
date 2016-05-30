package io.github.projectchroma.launcher.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import io.github.projectchroma.launcher.Launcher;

public class WebInterface{
	private static final String GH_USER = "ProjectChroma", GH_REPO = "Chroma";
	public static GithubCommit getLatestChromaVersion(String branch) throws IOException{
		return Launcher.GSON.fromJson(new InputStreamReader(new URL("https://api.github.com/repos/" + GH_USER + "/" + GH_REPO + "/commits/" + branch).openStream()), GithubCommit.class);
	}
	public static void download(String url, File file) throws IOException{
		Launcher.log().write("Downloading " + url + " to " + file, Log.DEBUG);
		Files.copy(new URL(url).openStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
		Launcher.log().write("Download complete", Log.DEBUG);
	}
	public static class GithubCommit{
		public String sha;
		public Commit commit;
		public static class Commit{
			public String message;
		}
	}
}
