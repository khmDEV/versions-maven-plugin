package org.codehaus.mojo.versions.api;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import com.jcraft.jsch.ConfigRepository.Config;

public class GitFuntions {
	private static Git git;
	private static int offset = -1;

	public static void setOffset(int offset) {
		GitFuntions.offset = offset;
	}

	public static int getVersion() {

		try {
			File file = new File(".git");
			git = Git.open(file);
			git.fetch().call();
			
			Iterable<RevCommit> commits= git.log().call();
			int count = 0;
			for (RevCommit commit : commits) {
				count++;
			}
			return count + offset+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;

	}
}
