package io.github.projectchroma.launcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class WebInterface{
	private static final String GH_USER = "ProjectChroma", GH_REPO = "Chroma";
	public static GithubCommit getLatestVersion(String branch) throws IOException{
		return Launcher.GSON.fromJson(new InputStreamReader(new URL("https://api.github.com/repos/" + GH_USER + "/" + GH_REPO + "/commits/" + branch).openStream()), GithubCommit.class);
	}
	public static void download(String fileName, File file) throws IOException{
		Files.copy(new URL("https://raw.githubusercontent.com/ProjectChroma/Chroma/master/" + fileName).openStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
	}
	public static class GithubCommit{
		public String sha;
		public Commit commit;
		public static class Commit{
			public String message;
		}
	}
}
