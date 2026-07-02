import { useQuery } from '@apollo/client'
import { useNavigate } from 'react-router-dom'
import { GET_LIBROS } from '../queries/libros'
import LoadingSpinner from '../components/LoadingSpinner'
import ErrorMessage from '../components/ErrorMessage'

export default function Home() {
  const { loading, error, data } = useQuery(GET_LIBROS)
  const navigate = useNavigate()

  if (loading) return <LoadingSpinner message="Cargando libros..." />
  if (error) return <ErrorMessage message={error.message} />

  const libros = data?.libros ?? []

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-2xl font-bold text-gray-800">📚 Catálogo de Libros</h1>
        <span className="text-sm text-gray-400">{libros.length} libro{libros.length !== 1 ? 's' : ''}</span>
      </div>

      {libros.length === 0 ? (
        <div className="text-center py-16 text-gray-400">
          <p className="text-4xl mb-3">📭</p>
          <p>No hay libros en el catálogo.</p>
        </div>
      ) : (
        <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
          {libros.map((libro) => (
            <div
              key={libro.id}
              className="bg-white rounded-xl shadow-sm border border-gray-200 p-4 hover:shadow-md transition-shadow cursor-pointer group"
              onClick={() => navigate(`/libro?id=${libro.id}`)}
            >
              <h2 className="font-semibold text-gray-900 text-base leading-snug group-hover:text-indigo-700 transition-colors">
                {libro.titulo}
              </h2>
              <button
                className="text-sm text-indigo-600 mt-1 hover:underline text-left"
                onClick={(e) => {
                  e.stopPropagation()
                  navigate(`/autor?id=${libro.autor.id}`)
                }}
              >
                {libro.autor.nombre}
              </button>
              <p className="text-xs text-gray-400 mt-1">{libro.anio}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}
