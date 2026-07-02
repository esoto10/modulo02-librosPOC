import { gql } from '@apollo/client'

export const GET_CATEGORIAS = gql`
  query GetCategorias {
    categorias {
      id
      nombre
    }
  }
`

export const GET_CATEGORIAS_CON_LIBROS = gql`
  query GetCategoriasConLibros {
    categorias {
      id
      nombre
      libros {
        id
        titulo
        anio
        autor {
          id
          nombre
        }
      }
    }
  }
`
