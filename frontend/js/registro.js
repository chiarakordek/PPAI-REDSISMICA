guardarBtn = document.getElementById("guardarBtn")

guardarBtn.addEventListener("click", function () {
  const fecha = document.getElementById("fecha").value;
  const ubicacion = document.getElementById("ubicacion").value;
  const magnitud = document.getElementById("magnitud").value;
  const profundidad = document.getElementById("profundidad").value;
  const descripcion = document.getElementById("descripcion").value;

  console.log("Nuevo registro:");
  console.log({ fecha, ubicacion, magnitud, profundidad, descripcion });

  alert("Registro guardado correctamente (esto es solo un mockup)");
});
