import React, { useRef, useEffect, useState } from "react";
import CanvasDraw from "react-canvas-draw";
import { useParams, useHistory } from "react-router-dom";
import BoardList from "./BoardList";
import "../App.css";

const Board = () => {
  const { id } = useParams();
  const history = useHistory(); // Reemplazo de useNavigate
  const firstCanvas = useRef(null);
  const [images, setImages] = useState([]);
  const [selectedImageIndex, setSelectedImageIndex] = useState(null);
  const [dragStartPos, setDragStartPos] = useState({ x: 0, y: 0 });
  const [dragging, setDragging] = useState(false);
  const [resizing, setResizing] = useState(false);
  const [isErasing, setIsErasing] = useState(false);
  const fileInputRef = useRef(null);

  useEffect(() => {
    const handlePaste = (event) => {
      const items = (event.clipboardData || event.originalEvent.clipboardData).items;
      for (let index in items) {
        const item = items[index];
        if (item.kind === "file") {
          const blob = item.getAsFile();
          const reader = new FileReader();
          reader.onload = (e) => {
            setImages((prevImages) => [
              ...prevImages,
              { src: e.target.result, x: 100, y: 100, width: 100, height: 100 },
            ]);
          };
          reader.readAsDataURL(blob);
        }
      }
    };

    const handleKeyDown = (event) => {
      if (event.ctrlKey && event.key === "z") {
        event.preventDefault();
        undo();
      }
    };

    document.addEventListener("paste", handlePaste);
    document.addEventListener("keydown", handleKeyDown);

    return () => {
      document.removeEventListener("paste", handlePaste);
      document.removeEventListener("keydown", handleKeyDown);
    };
  }, []);

  const handleMouseDown = (event, index, resize = false) => {
    setSelectedImageIndex(index);
    setDragStartPos({ x: event.clientX, y: event.clientY });
    setDragging(!resize);
    setResizing(resize);
    event.stopPropagation();
  };

  const handleMouseMove = (event) => {
    if (dragging && selectedImageIndex !== null) {
      const deltaX = event.clientX - dragStartPos.x;
      const deltaY = event.clientY - dragStartPos.y;
      setImages((prevImages) => {
        const updatedImages = [...prevImages];
        updatedImages[selectedImageIndex].x += deltaX;
        updatedImages[selectedImageIndex].y += deltaY;
        return updatedImages;
      });
      setDragStartPos({ x: event.clientX, y: event.clientY });
    }

    if (resizing && selectedImageIndex !== null) {
      const deltaX = event.clientX - dragStartPos.x;
      const deltaY = event.clientY - dragStartPos.y;
      setImages((prevImages) => {
        const updatedImages = [...prevImages];
        updatedImages[selectedImageIndex].width += deltaX;
        updatedImages[selectedImageIndex].height += deltaY;
        return updatedImages;
      });
      setDragStartPos({ x: event.clientX, y: event.clientY });
    }
  };

  const handleMouseUp = () => {
    setDragging(false);
    setResizing(false);
  };

  const clear = () => {
    firstCanvas.current.clear();
    setImages([]);
  };

  const undo = () => {
    firstCanvas.current.undo();
  };

  const downloadWithBlankBackground = () => {
    const canvas = firstCanvas.current.canvas.drawing;
    const tempCanvas = document.createElement("canvas");
    tempCanvas.width = canvas.width;
    tempCanvas.height = canvas.height;
    const tempCtx = tempCanvas.getContext("2d");

    // Dibujar fondo blanco en el canvas temporal
    tempCtx.fillStyle = "#ffffff";
    tempCtx.fillRect(0, 0, tempCanvas.width, tempCanvas.height);

    // Dibujar el contenido del lienzo en el canvas temporal
    tempCtx.drawImage(canvas, 0, 0);

    // Dibujar las imágenes en el canvas temporal
    images.forEach((image) => {
      const img = new Image();
      img.src = image.src;
      tempCtx.drawImage(img, image.x, image.y, image.width, image.height);
    });

    // Convertir el canvas temporal en una URL de datos en formato PNG
    const dataURL = tempCanvas.toDataURL("image/png");

    // Descargar la imagen
    downloadDataURL(dataURL, "tablero.png");
  };

  const downloadDataURL = (dataURL, filename) => {
    const link = document.createElement("a");
    link.href = dataURL;
    link.download = filename;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  };

  const toggleEraser = () => {
    setIsErasing(!isErasing);
  };

  const handleImageUpload = (event) => {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = (e) => {
      setImages((prevImages) => [
        ...prevImages,
        { src: e.target.result, x: 100, y: 100, width: 100, height: 100 },
      ]);
    };
    reader.readAsDataURL(file);
  };

  const openFileDialog = () => {
    fileInputRef.current.click(); // Simular clic en el input de archivo
  };

  return (
    <div>
      <BoardList />
      <div style={{ position: "relative" }} onMouseMove={handleMouseMove} onMouseUp={handleMouseUp}>
        <div
          style={{
            position: "absolute",
            top: 10,
            left: 10,
            zIndex: 1000,
          }}
        >
          <button onClick={clear}>Limpiar Dibujo</button>
          <button onClick={undo}>Deshacer</button>
          <button onClick={toggleEraser}>
            {isErasing ? "Desactivar Borrador" : "Activar Borrador"}
          </button>
          <button onClick={downloadWithBlankBackground}>Descargar imagen</button>
          <button onClick={openFileDialog}>Insertar Imagen</button>
          <input
            type="file"
            accept="image/*"
            onChange={handleImageUpload}
            style={{ display: "none" }}
            ref={fileInputRef} // Referencia para el input de archivo
          />
        </div>
        <CanvasDraw
          brushRadius={3}
          brushColor={isErasing ? "white" : "red"}
          canvasWidth={3000}
          canvasHeight={1000}
          hideGrid={false}
          style={{ border: "1px solid black" }}
          ref={firstCanvas}
        />
        {images.map((image, index) => (
          <div
            key={index}
            className="image-container"
            style={{
              position: "absolute",
              left: `${image.x}px`,
              top: `${image.y}px`,
              width: `${image.width}px`,
              height: `${image.height}px`,
              cursor: "move",
              border: selectedImageIndex === index ? "2px solid blue" : "none",
            }}
            onMouseDown={(event) => handleMouseDown(event, index)}
          >
            <img
              src={image.src}
              alt="pasted"
              style={{
                width: "100%",
                height: "100%",
                pointerEvents: "none",
              }}
            />
            <div
              style={{
                position: "absolute",
                bottom: 0,
                right: 0,
                width: "10px",
                height: "10px",
                backgroundColor: "blue",
                cursor: "nwse-resize",
              }}
              onMouseDown={(event) => handleMouseDown(event, index, true)}
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default Board;
