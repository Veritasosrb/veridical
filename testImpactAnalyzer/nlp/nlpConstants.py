import os
from pathlib import Path

GIT_HISTORY_PATH = str(Path(os.getcwd()).parent.absolute()) + os.path.sep + \
    "pythonScripts" + os.path.sep + "gitHistory.json"
TEST_DATA_JSON_PATH = str(Path(os.getcwd()).parent.absolute(
)) + os.path.sep + "src" + os.path.sep + "main" + os.path.sep + "resources" + os.path.sep + "testdata" + os.path.sep + "bookMyMovieTia-main" + os.path.sep + "src" + os.path.sep + "tcLabelComponents.json"

