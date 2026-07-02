import { useState } from 'react'
import { useMutation, useQuery } from '@apollo/client'
import { useNavigate } from 'react-router-dom'
import { AGREGAR_LIBRO } from '../queries/mutations'
import { GET_AUTORES } from '../queries/autores'
import { GET_CATEGORIAS } from '../queries/categorias'
import { GET_LIBROS } from '../queries/libros'
import LoadingSpinner from '../components/LoadingSpinner'
import ErrorMessage from '../components/ErrorMessage'

const INITIAL_FORM = {
  titulo: '',
  paginas: '',
  anio: '',
  autorId: '',
  categoriaIds: [],
}

export default function AgregarLibro() {
  const navigate = useNavigate()
  const [form, setForm] = useState(INITIAL_FORM)
  const [successMsg, setSuccessMsg] = useState('')

  const { data: autoresData, loading: autoresLoading, error: autoresError } = useQuery(GET_AUTORES)
  const { data: categoriasData, loading: categoriasLoading, error: categoriasError } = useQuery(GET_CATEGORIAS)

  const [agregarLibro, { loading: mutLoading, error: mutError }] = useMutation(AGREGAR_LIBRO, {
    refetchQueries: [{ query: GET_LIBROS }],
  })

  const handleChange = (e) => {
    const { name, value } = e.target
    setForm((prev) => ({ ...prev, [name]: value }))
  }

  const handleCategoriaToggle = (id) => {
    setForm((prev) => ({
      ...prev,
      categoriaIds: prev.categoriaIds.includes(id)
        ? prev.categoriaIds.filter((c) => c !== id)
        : [...prev.categoriaIds, id],
    }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setSuccessMsg('')
    try {
      const input = {
        titulo: form.titulo.trim(),
        paginas: parseInt(form.paginas, 10),
        anio: parseInt(form.anio, 10),
        autorId: form.autorId,
        categoriaIds: form.categoriaIds.length > 0 ? form.categoriaIds : null,
        coleccionId: null,
      }
      const result = await agregarLibro({ variables: { input } })
      setSuccessMsg(`"${result.data.agregarLibro.titulo}" fue agregado exitosamente.`)
      setForm(INITIAL_FORM)
    } catch (_) {
      // error surface via mutError
    }
  }

  if (autoresLoading || categoriasLoading) {
    return <LoadingSpinner message="Cargando formulario..." />
  }

  if (autoresError) return <ErrorMessage message={autoresError.message} />
  if (categoriasError) return <ErrorMessage message={categoriasError.message} />

  const autores = autoresData?.autores ?? []
  const categorias = categoriasData?.categorias ?? []

  return (
    <div>
      <h1 className="text-2xl font-bold text-gray-800 mb-6">➕ Agregar Libro</h1>

      <div className="max-w-2xl bg-white rounded-xl shadow-sm border border-gray-200 p-6">
        {successMsg && (
          <div className="mb-5 rounded-lg bg-green-50 border border-green-200 p-3 text-green-700 text-sm flex gap-2 items-center">
            <span>✅</span>
            <span>{successMsg}</span>
            <button
              onClick={() => navigate('/')}
              className="ml-auto text-green-800 underline text-xs"
            >
              Ver catálogo
            </button>
          </div>
        )}

        {mutError && (
          <div className="mb-5">
            <ErrorMessage message={mutError.message} />
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-5">
          {/* Título */}
          <div>
            <label htmlFor="titulo" className="block text-sm font-medium text-gray-700 mb-1">
              Título <span className="text-red-500">*</span>
            </label>
            <input
              id="titulo"
              name="titulo"
              type="text"
              value={form.titulo}
              onChange={handleChange}
              required
              placeholder="Título del libro"
              className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-400"
            />
          </div>

          {/* Páginas y Año en fila */}
          <div className="grid grid-cols-2 gap-4">
            <div>
              <label htmlFor="paginas" className="block text-sm font-medium text-gray-700 mb-1">
                Páginas <span className="text-red-500">*</span>
              </label>
              <input
                id="paginas"
                name="paginas"
                type="number"
                min="1"
                value={form.paginas}
                onChange={handleChange}
                required
                placeholder="Ej: 320"
                className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-400"
              />
            </div>
            <div>
              <label htmlFor="anio" className="block text-sm font-medium text-gray-700 mb-1">
                Año <span className="text-red-500">*</span>
              </label>
              <input
                id="anio"
                name="anio"
                type="number"
                min="1000"
                max="2100"
                value={form.anio}
                onChange={handleChange}
                required
                placeholder="Ej: 2023"
                className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-400"
              />
            </div>
          </div>

          {/* Autor */}
          <div>
            <label htmlFor="autorId" className="block text-sm font-medium text-gray-700 mb-1">
              Autor <span className="text-red-500">*</span>
            </label>
            <select
              id="autorId"
              name="autorId"
              value={form.autorId}
              onChange={handleChange}
              required
              className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-400 bg-white"
            >
              <option value="">Selecciona un autor...</option>
              {autores.map((autor) => (
                <option key={autor.id} value={autor.id}>
                  {autor.nombre} ({autor.nacionalidad})
                </option>
              ))}
            </select>
            {autores.length === 0 && (
              <p className="text-xs text-amber-600 mt-1">
                No hay autores registrados.{' '}
              </p>
            )}
          </div>

          {/* Géneros (multi-select con pills) */}
          <div>
            <p className="block text-sm font-medium text-gray-700 mb-2">
              Géneros <span className="text-gray-400 font-normal">(opcional)</span>
            </p>
            {categorias.length === 0 ? (
              <p className="text-xs text-gray-400">No hay géneros disponibles.</p>
            ) : (
              <div className="flex flex-wrap gap-2">
                {categorias.map((cat) => (
                  <button
                    key={cat.id}
                    type="button"
                    onClick={() => handleCategoriaToggle(cat.id)}
                    className={`px-3 py-1 rounded-full text-xs font-medium border transition-colors ${
                      form.categoriaIds.includes(cat.id)
                        ? 'bg-indigo-600 text-white border-indigo-600'
                        : 'bg-white text-gray-600 border-gray-300 hover:border-indigo-400 hover:text-indigo-600'
                    }`}
                  >
                    {form.categoriaIds.includes(cat.id) && <span className="mr-1">✓</span>}
                    {cat.nombre}
                  </button>
                ))}
              </div>
            )}
          </div>

          {/* Acciones */}
          <div className="pt-2 flex gap-3 border-t border-gray-100">
            <button
              type="submit"
              disabled={mutLoading}
              className="bg-indigo-600 text-white px-6 py-2.5 rounded-lg text-sm font-semibold hover:bg-indigo-700 transition-colors disabled:opacity-60 disabled:cursor-not-allowed"
            >
              {mutLoading ? 'Guardando...' : 'Agregar Libro'}
            </button>
            <button
              type="button"
              onClick={() => navigate('/')}
              className="border border-gray-300 text-gray-600 px-6 py-2.5 rounded-lg text-sm font-medium hover:bg-gray-50 transition-colors"
            >
              Cancelar
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}
