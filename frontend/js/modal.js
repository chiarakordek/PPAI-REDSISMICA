function ampliarImagen(src) {
  const modal = document.getElementById('modal-imagen');
  const modalImg = document.getElementById("imagen-ampliada");
  const captionText = document.getElementById("pie-imagen");
  
  modal.style.display = "block";
  modalImg.src = src;
  captionText.innerHTML = src.split('/').pop(); // Muestra el nombre del archivo
}

function cerrarModal() {
  document.getElementById('modal-imagen').style.display = "none";
}

// Cerrar al hacer clic fuera de la imagen
window.onclick = function(event) {
  const modal = document.getElementById('modal-imagen');
  if (event.target == modal) {
    modal.style.display = "none";s
  }
}