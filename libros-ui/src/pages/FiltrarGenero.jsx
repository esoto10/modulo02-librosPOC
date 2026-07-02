import { useState } from 'react'
import { useQuery } from '@apollo/client'
import { useNavigate } from 'react-router-dom'
import { GET_CATEGORIAS_CON_LIBROS } from '../queries/categorias'
import LoadingSpinner from '../components/LoadingSpinner'
import ErrorMessage from '../components/ErrorMessage'

export default function FiltrarGenero() {
  const [selectedId, setSelectedId] = useState(null)
  const navigate = useNavigate()

  const { loading, error, data } = useQuery(GET_CATEGORIAS_CON_LIBROS)

  if (loading) return <LoadingSpinner message="Cargando géneros..." />
  if (error) return <ErrorMessage message={error.message} />

  const categorias = data?.categorias ?? []
  const selected = categorias.find((c) => c.id === selectedId)

  return (
    <div>
      <h1 className="text-2xl font-bold text-gray-800 mb-6">🏷️ Filtrar por Género</h1>

      <div className="flex gap-6">
        {/* Sidebar: lista de géneros */}
        <aside className="w-48 shrink-0">
          <h2 className="text-xs uppercase tracking-widest text-gray-400 font-semibold mb-3">
            Géneros ({categorias.length})
          </h2>
          {categorias.length === 0 ? (
            <p className="text-sm text-gray-400">Sin géneros disponibles.</p>
          ) : (
            <ul className="space-y-1">
              {categorias.map((cat) => (
                <li key={cat.id}>
                  <button
                    onClick={() => setSelectedId(cat.id)}
                    className={`w-full text-left px-3 py-2 rounded-lg text-sm font-medium transition-colors ${
                      selectedId === cat.id
                        ? 'bg-indigo-600 text-white'
                        : 'text-gray-700 hover:bg-gray-100'
                    }`}
                  >
                    {cat.nombre}
                    <span className={`ml-1.5 text-xs ${selectedId === cat.id ? 'opacity-75' : 'text-gray-400'}`}>
                      ({cat.libros.length})
                    </span>
                  </button>
                </li>
              ))}
            </ul>
          )}
        </aside>

        {/* Panel: libros del género seleccionado */}
        <div className="flex-1 min-w-0">
          {!selected ? (
            <div className="flex flex-col items-center justify-center h-48 text-gray-400 gap-2">
              <span className="text-4xl">👈</span>
              <p className="text-sm">Selecciona un género para ver sus libros.</p>
            </div>
          ) : (
            <>
              <h2 className="text-lg font-semibold text-gray-800 mb-4">
                Libros de{' '}
                <span className="text-indigo-600">{selected.nombre}</span>
                <span className="ml-2 text-sm font-normal text-gray-400">
                  ({selected.libros.length})
                </span>
              </h2>

              {selected.libros.length === 0 ? (
                <p className="text-gray-400 text-sm">No hay libros en este género.</p>
              ) : (
                <div className="grid gap-3 sm:grid-cols-2">
                  {selected.libros.map((libro) => (
                    <div
                      key={libro.id}
                      className="bg-white rounded-lg border border-gray-200 p-4 hover:shadow-sm cursor-pointer transition-shadow group"
                      onClick={() => navigate(`/libro?id=${libro.id}`)}
                    >
                      <p className="font-semibold text-gray-900 text-sm group-hover:text-indigo-700 transition-colors">
                        {libro.titulo}
                      </p>
                      <button
                        className="text-xs text-indigo-500 mt-0.5 hover:underline"
                        onClick={(e) => {
                          e.stopPropagation()
                          navigate(`/autor?id=${libro.autor.id}`)
                        }}
                      >
                        {libro.autor.nombre}
                      </button>
                      <p className="text-xs text-gray-400">{libro.anio}</p>
                    </div>
                  ))}
                </div>
              )}
            </>
          )}
        </div>
      </div>
    </div>
  )
}
