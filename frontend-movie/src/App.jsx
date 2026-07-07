
import "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Movies from "./components/Movies.jsx";
import Booking from "./components/Booking.jsx";

function App() {
  return (
    <BrowserRouter>
      <div className="app">
        <Routes>
          <Route path="/" element={<Movies />} />
          <Route path="/booking/:movieId" element={<Booking />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
