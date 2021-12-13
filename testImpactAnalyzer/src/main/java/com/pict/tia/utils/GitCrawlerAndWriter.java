package com.pict.tia.utils;

import org.repodriller.domain.Commit;
import org.repodriller.domain.DiffParser;
import org.repodriller.domain.Modification;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

public class GitCrawlerAndWriter implements CommitVisitor 
{
	@Override
	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer)
	{
		for(Modification m : commit.getModifications()) {
			DiffParser parsedDiff = new DiffParser(m.getDiff());
			writer.write(
					commit.getHash(),
					commit.getAuthor().getName(),
					commit.getCommitter().getName(),
					m.getFileName(),
					m.getNewPath(),
					m.getOldPath(),
					m.getType(),
					parsedDiff.getBlocks().get(0).getLinesInOldFile(),
					parsedDiff.getBlocks().get(0).getLinesInNewFile()
			);
		}
	}
}
