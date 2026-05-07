const form = document.getElementById("contact-form");
const nameInput = document.getElementById("contact-name");
const emailInput = document.getElementById("contact-email");
const messageInput = document.getElementById("contact-message");
const errorText = document.getElementById("form-error");
const successText = document.getElementById("form-success");

if (form) {
  form.addEventListener("submit", (event) => {
    event.preventDefault();
    errorText.textContent = "";
    successText.textContent = "";

    const name = nameInput.value.trim();
    const email = emailInput.value.trim();
    const message = messageInput.value.trim();

    if (!name || !email || !message) {
      errorText.textContent = "Please fill in all fields.";
      return;
    }

    if (!email.includes("@")) {
      errorText.textContent = "Please enter a valid email address.";
      return;
    }

    successText.textContent = "Thanks! We will reach out shortly.";
    form.reset();
  });
}
