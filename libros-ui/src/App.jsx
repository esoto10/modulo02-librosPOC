import { Routes, Route } from 'react-router-dom'
import Navbar from './components/Navbar'
import Home from './pages/Home'
import LibroDetalle from './pages/LibroDetalle'
import AutorDetalle from './pages/AutorDetalle'
import FiltrarGenero from './pages/FiltrarGenero'
import AgregarLibro from './pages/AgregarLibro'

export default function App() {
  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />
      <main className="max-w-5xl mx-auto px-4 py-8">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/libro" element={<LibroDetalle />} />
          <Route path="/autor" element={<AutorDetalle />} />
          <Route path="/generos" element={<FiltrarGenero />} />
          <Route path="/agregar" element={<AgregarLibro />} />
        </Routes>
      </main>
    </div>
  )
}
