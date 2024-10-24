function exportToGitHub() {
  // Step 1: Get the document content
  var doc = DocumentApp.getActiveDocument();
  var content = doc.getBody().getText();
  
  // Step 2: Define GitHub repository details
  var repo = 'your-username/your-repo'; // Replace with your GitHub username and repo name
  var filePath = 'docs/your-doc.md'; // Path where you want the file to be stored (e.g., docs/filename.md)
  var branch = 'main'; // Branch where you want to commit (e.g., 'main', 'master', or another branch)
  var token = 'your-personal-access-token'; // Replace with your GitHub PAT (Personal Access Token)

  // Step 3: Check if the file already exists in the repository (to get the SHA for updates)
  var url = 'https://api.github.com/repos/' + repo + '/contents/' + filePath;
  var options = {
    'method': 'get',
    'headers': {
      'Authorization': 'token ' + token
    }
  };
  
  var response = UrlFetchApp.fetch(url, options);
  var responseData = JSON.parse(response.getContentText());
  
  var sha = responseData.sha || null; // Get the SHA of the existing file if it exists
  
  // Step 4: Prepare the file content for GitHub (base64 encoding)
  var encodedContent = Utilities.base64Encode(content);
  
  // Step 5: Create a PUT request to upload or update the file
  var payload = {
    'message': 'Updated from Google Docs',
    'content': encodedContent,
    'branch': branch
  };
  
  if (sha) {
    payload.sha = sha; // Include the file's SHA if it's an update
  }
  
  var putOptions = {
    'method': 'put',
    'contentType': 'application/json',
    'headers': {
      'Authorization': 'token ' + token
    },
    'payload': JSON.stringify(payload)
  };
  
  // Step 6: Send the request to update the file
  var putResponse = UrlFetchApp.fetch(url, putOptions);
  
  // Log the response for debugging purposes
  Logger.log(putResponse.getContentText());
}
