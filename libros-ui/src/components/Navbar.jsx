import { NavLink } from 'react-router-dom'

const links = [
  { to: '/', label: '📚 Inicio' },
  { to: '/libro', label: '🔎 Libro' },
  { to: '/autor', label: '👤 Autor' },
  { to: '/generos', label: '🏷️ Géneros' },
  { to: '/agregar', label: '➕ Agregar' },
]

export default function Navbar() {
  return (
    <nav className="bg-indigo-700 shadow-md">
      <div className="max-w-5xl mx-auto px-4 flex items-center gap-1 h-14 overflow-x-auto">
        <span className="text-white font-bold text-lg mr-4 shrink-0">📖 Libros POC</span>
        {links.map(({ to, label }) => (
          <NavLink
            key={to}
            to={to}
            end={to === '/'}
            className={({ isActive }) =>
              `px-3 py-1.5 rounded text-sm font-medium transition-colors whitespace-nowrap ${
                isActive
                  ? 'bg-white text-indigo-700'
                  : 'text-indigo-100 hover:bg-indigo-600'
              }`
            }
          >
            {label}
          </NavLink>
        ))}
      </div>
    </nav>
  )
}
