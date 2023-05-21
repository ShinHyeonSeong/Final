// Sample data - example comments
const comments = [
    "Nice comment!",
    "Great job!",
    "Awesome!",
    "Keep it up!",
    "Well done!",
    "Impressive!",
  ];
  
  // Function to render comments
  function renderComments() {
    const commentContainer = document.getElementById("comment-container");
    commentContainer.innerHTML = "";
  
    comments.forEach((comment) => {
      const commentElement = document.createElement("div");
      commentElement.classList.add("comment");
      commentElement.textContent = comment;
  
      commentContainer.appendChild(commentElement);
    });
  }
  
  // Function to add a new comment
  function addComment(event) {
    event.preventDefault();
  
    const commentInput = document.getElementById("comment-content");
    const newComment = commentInput.value;
  
    comments.push(newComment);
  
    commentInput.value = "";
  
    renderComments();
  }
  
  // Event listener for submitting the comment form
  const commentForm = document.getElementById("comment-form");
  commentForm.addEventListener("submit", addComment);
  
  // Initial rendering of comments
  renderComments();
  