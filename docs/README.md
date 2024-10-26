# Integrate Google docs into GitHub
By: Javier Ortega Mendoza -> [javsort](https://github.com/javsort)

## Pre-requisites

### 1. GitHub Personal Access Token
- Retrieve a Personal Access Token (PAT) from GH:
    - From Your GH Profile -> Developer Settings -> Personal Access Tokens -> Generate New Token
    - Choose CLASSIC, generate the token for 90 days & give it all permissions
    - Save the PAT, **ITS ONLY DISPLAYED ONCE**

### 2. Google Docs Permissions
- On Google Docs, go to **Extensions** -> **Apps Script**
    - Make your copy of the [Docs Script](./docs_script) called `github-integration-d<deliverable #>-<your-name>.gs`
    - Add it below in the scripts from the rest of the team
    - Replace all the necessary fields:
        - `repo` -> should be link to main repo, for this project it's `javsort/Linkaster`
        - `filePath` -> MAKE SURE to fill this properly, else it'll create a new file. These are the paths for the deliverables so far (this list will be updated as more docs are added):
            - Deliverable 1 doc = `docs/delivery_1.md`
        - `token` -> here's where you should add your PAT from the steps earlier

## How-to

### First use
Once your file is ready, **EACH TIME** you've done the changes you worked on for the day, in Google Docs go to **Extensions** -> **Apps Script** again, get to your personal script, and click on the **Run** Button.

For the first time, google will ask for user authentication, follow the steps and approve all, the first upload should be committed to the `main` branch.

## :warning: Important Notice
**THIS IS THE ONLY SCRIPT ALLOWED TO COMMIT TO MAIN**, for all other commits, YOU MUST OPEN A **Pull-Request** and **wait** for approval.

### Regular use
After the first use of the script, it is sufficient to just click **Run** on your personal script to add your update to the repository


## FAQ - Frequently Asked Questions
1. Can I add pictures to the Google Docs? -> Yes, they are not added to the markdown file of the deliverable, shouldn't cause any problems. 

2. I want people to know what picture/diagram I uploaded, how do I add it? -> this repo has folders for `/diagram_shortcuts` & `/image_shortcuts`, it is sufficient to add your `.url` file from Google Drive, OneDrive, whatever to it. This helps to ensure proper storage and avoid issues between commits, while still referencing who worked on what for the repo.
