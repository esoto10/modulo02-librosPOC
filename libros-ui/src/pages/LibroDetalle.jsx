import { useState, useEffect } from 'react'
import { useLazyQuery } from '@apollo/client'
import { useNavigate, useSearchParams } from 'react-router-dom'
import { GET_LIBRO } from '../queries/libros'
import LoadingSpinner from '../components/LoadingSpinner'
import ErrorMessage from '../components/ErrorMessage'

export default function LibroDetalle() {
  const [searchParams] = useSearchParams()
  const [inputId, setInputId] = useState(searchParams.get('id') ?? '')
  const navigate = useNavigate()

  const [getLibro, { loading, error, data }] = useLazyQuery(GET_LIBRO)

  useEffect(() => {
    const id = searchParams.get('id')
    if (id) getLibro({ variables: { id } })
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  const handleSearch = (e) => {
    e.preventDefault()
    const id = inputId.trim()
    if (id) getLibro({ variables: { id } })
  }

  const libro = data?.libro

  return (
    <div>
      <h1 className="text-2xl font-bold text-gray-800 mb-6">🔎 Detalle de Libro</h1>

      <form onSubmit={handleSearch} className="flex gap-2 mb-8 max-w-lg">
        <input
          type="text"
          value={inputId}
          onChange={(e) => setInputId(e.target.value)}
          placeholder="Ingresa el ID del libro..."
          className="flex-1 border border-gray-300 rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-400"
        />
        <button
          type="submit"
          className="bg-indigo-600 text-white px-5 py-2 rounded-lg text-sm font-medium hover:bg-indigo-700 transition-colors"
        >
          Buscar
        </button>
      </form>

      {loading && <LoadingSpinner message="Buscando libro..." />}
      {error && <ErrorMessage message={error.message} />}

      {!loading && data && !libro && (
        <div className="text-center py-12 text-gray-400">
          <p className="text-3xl mb-2">🔍</p>
          <p>No se encontró ningún libro con ese ID.</p>
        </div>
      )}

      {libro && (
        <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-6 max-w-2xl">
          <h2 className="text-xl font-bold text-gray-900 mb-5">{libro.titulo}</h2>
          <dl className="grid sm:grid-cols-2 gap-x-8 gap-y-4 text-sm">
            <div>
              <dt className="text-xs uppercase tracking-wide text-gray-400 font-semibold mb-0.5">ID</dt>
              <dd className="text-gray-800">{libro.id}</dd>
            </div>
            <div>
              <dt className="text-xs uppercase tracking-wide text-gray-400 font-semibold mb-0.5">Año</dt>
              <dd className="text-gray-800">{libro.anio}</dd>
            </div>
            <div>
              <dt className="text-xs uppercase tracking-wide text-gray-400 font-semibold mb-0.5">Páginas</dt>
              <dd className="text-gray-800">{libro.paginas}</dd>
            </div>
            <div>
              <dt className="text-xs uppercase tracking-wide text-gray-400 font-semibold mb-0.5">Colección</dt>
              <dd className="text-gray-800">
                {libro.coleccion ? `${libro.coleccion.titulo} (${libro.coleccion.anio})` : '—'}
              </dd>
            </div>
            <div className="sm:col-span-2">
              <dt className="text-xs uppercase tracking-wide text-gray-400 font-semibold mb-0.5">Autor</dt>
              <dd>
                <button
                  onClick={() => navigate(`/autor?id=${libro.autor.id}`)}
                  className="text-indigo-600 hover:underline font-medium"
                >
                  {libro.autor.nombre}
                </button>
                <span className="text-gray-400 text-xs ml-2">({libro.autor.nacionalidad})</span>
              </dd>
            </div>
            <div className="sm:col-span-2">
              <dt className="text-xs uppercase tracking-wide text-gray-400 font-semibold mb-2">Géneros</dt>
              <dd className="flex flex-wrap gap-2">
                {libro.categorias.length > 0 ? (
                  libro.categorias.map((cat) => (
                    <span
                      key={cat.id}
                      className="bg-indigo-50 text-indigo-700 text-xs font-medium px-2.5 py-1 rounded-full border border-indigo-100"
                    >
                      {cat.nombre}
                    </span>
                  ))
                ) : (
                  <span className="text-gray-400">Sin géneros asignados</span>
                )}
              </dd>
            </div>
          </dl>
        </div>
      )}
    </div>
  )
}
