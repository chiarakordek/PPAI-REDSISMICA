document.getElementById("login-form").addEventListener("submit", function (e) {
  e.preventDefault();

  const datos = {
    username: document.getElementById("usuario").value,
    password: document.getElementById("password").value
  };

  fetch("http://localhost:8080/api/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(datos)
  })
    .then(response => {
      if (response.ok) {
        window.location.href = "menu.html";
      } else {
        alert("Credenciales incorrectas");
      }
    })
    .catch(error => {
      console.error("Error de conexión:", error);
    });
});
