// Function to open a new window and display note content
function viewNote() {
  const noteContent = document.getElementById('content').value;
  const noteWindow = window.open('', '_blank', 'width=500,height=300');
  noteWindow.document.write(`
    <!DOCTYPE html>
    <html lang="en">
    <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" href="styles.css">
      <title>Note</title>
    </head>
    <body>
      <div class="note-container">
        <h2>Note Content</h2>
        <p>${noteContent}</p>
      </div>
    </body>
    </html>
  `);
  noteWindow.document.close();
}

// Event listeners for buttons
const sendBtn = document.getElementById('send-btn');
const viewBtn = document.getElementById('view-btn');

sendBtn.addEventListener('click', () => {
  // Logic to send the message
  console.log('Message sent!');
});

viewBtn.addEventListener('click', viewNote);
