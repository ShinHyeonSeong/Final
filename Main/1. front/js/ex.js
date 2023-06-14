// Sample data for message list
const messages = [
    { subject: 'Hello', content: 'This is a test message.' },
    { subject: 'Important Notice', content: 'Please read this message carefully.' },
    { subject: 'Reminder', content: 'Don\'t forget about our meeting tomorrow.' }
  ];
  
  // Function to render message list
  function renderMessageList() {
    const messageList = document.getElementById('message-list');
    
    messages.forEach(message => {
      const messageItem = document.createElement('li');
      messageItem.className = 'message-item';
      messageItem.innerHTML = `
        <h2>${message.subject}</h2>
        <p>${message.content}</p>
      `;
      messageList.appendChild(messageItem);
    });
  }
  
  // Render the message list when the page loads
  window.addEventListener('load', renderMessageList);
  