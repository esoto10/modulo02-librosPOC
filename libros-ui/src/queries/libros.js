import { gql } from '@apollo/client'

export const GET_LIBROS = gql`
  query GetLibros {
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
`

export const GET_LIBRO = gql`
  query GetLibro($id: ID!) {
    libro(id: $id) {
      id
      titulo
      paginas
      anio
      autor {
        id
        nombre
        nacionalidad
      }
      coleccion {
        id
        titulo
        anio
      }
      categorias {
        id
        nombre
      }
    }
  }
`
