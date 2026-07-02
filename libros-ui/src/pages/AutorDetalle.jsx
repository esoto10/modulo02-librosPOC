import { useState, useEffect } from 'react'
import { useLazyQuery } from '@apollo/client'
import { useNavigate, useSearchParams } from 'react-router-dom'
import { GET_AUTOR } from '../queries/autores'
import LoadingSpinner from '../components/LoadingSpinner'
import ErrorMessage from '../components/ErrorMessage'

export default function AutorDetalle() {
  const [searchParams] = useSearchParams()
  const [inputId, setInputId] = useState(searchParams.get('id') ?? '')
  const navigate = useNavigate()

  const [getAutor, { loading, error, data }] = useLazyQuery(GET_AUTOR)

  useEffect(() => {
    const id = searchParams.get('id')
    if (id) getAutor({ variables: { id } })
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  const handleSearch = (e) => {
    e.preventDefault()
    const id = inputId.trim()
    if (id) getAutor({ variables: { id } })
  }

  const autor = data?.autor

  return (
    <div>
      <h1 className="text-2xl font-bold text-gray-800 mb-6">👤 Detalle de Autor</h1>

      <form onSubmit={handleSearch} className="flex gap-2 mb-8 max-w-lg">
        <input
          type="text"
          value={inputId}
          onChange={(e) => setInputId(e.target.value)}
          placeholder="Ingresa el ID del autor..."
          className="flex-1 border border-gray-300 rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-400"
        />
        <button
          type="submit"
          className="bg-indigo-600 text-white px-5 py-2 rounded-lg text-sm font-medium hover:bg-indigo-700 transition-colors"
        >
          Buscar
        </button>
      </form>

      {loading && <LoadingSpinner message="Buscando autor..." />}
      {error && <ErrorMessage message={error.message} />}

      {!loading && data && !autor && (
        <div className="text-center py-12 text-gray-400">
          <p className="text-3xl mb-2">🔍</p>
          <p>No se encontró ningún autor con ese ID.</p>
        </div>
      )}

      {autor && (
        <div className="space-y-6">
          {/* Datos del autor */}
          <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
            <h2 className="text-xl font-bold text-gray-900 mb-4">{autor.nombre}</h2>
            <dl className="grid sm:grid-cols-2 gap-x-8 gap-y-3 text-sm">
              <div>
                <dt className="text-xs uppercase tracking-wide text-gray-400 font-semibold mb-0.5">ID</dt>
                <dd className="text-gray-800">{autor.id}</dd>
              </div>
              <div>
                <dt className="text-xs uppercase tracking-wide text-gray-400 font-semibold mb-0.5">Nacionalidad</dt>
                <dd className="text-gray-800">{autor.nacionalidad}</dd>
              </div>
            </dl>
          </div>

          {/* Lista de libros */}
          {autor.libros.length > 0 && (
            <div>
              <h3 className="text-lg font-semibold text-gray-700 mb-3">
                Libros
                <span className="ml-2 text-sm font-normal text-gray-400">({autor.libros.length})</span>
              </h3>
              <div className="grid gap-3 sm:grid-cols-2 lg:grid-cols-3">
                {autor.libros.map((libro) => (
                  <div
                    key={libro.id}
                    className="bg-white rounded-lg border border-gray-200 p-3 hover:shadow-sm cursor-pointer transition-shadow group"
                    onClick={() => navigate(`/libro?id=${libro.id}`)}
                  >
                    <p className="font-medium text-gray-900 text-sm group-hover:text-indigo-700 transition-colors">
                      {libro.titulo}
                    </p>
                    <p className="text-xs text-gray-400 mt-0.5">{libro.anio}</p>
                  </div>
                ))}
              </div>
            </div>
          )}

          {/* Colecciones */}
          {autor.colecciones.length > 0 && (
            <div>
              <h3 className="text-lg font-semibold text-gray-700 mb-3">
                Colecciones
                <span className="ml-2 text-sm font-normal text-gray-400">({autor.colecciones.length})</span>
              </h3>
              <div className="grid gap-3 sm:grid-cols-2">
                {autor.colecciones.map((col) => (
                  <div key={col.id} className="bg-white rounded-lg border border-gray-200 p-3">
                    <p className="font-medium text-gray-900 text-sm">{col.titulo}</p>
                    <p className="text-xs text-gray-400 mt-0.5">Desde {col.anio}</p>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      )}
    </div>
  )
}
