import { gql } from '@apollo/client'

export const GET_AUTORES = gql`
  query GetAutores {
    autores {
      id
      nombre
      nacionalidad
    }
  }
`

export const GET_AUTOR = gql`
  query GetAutor($id: ID!) {
    autor(id: $id) {
      id
      nombre
      nacionalidad
      libros {
        id
        titulo
        anio
      }
      colecciones {
        id
        titulo
        anio
      }
    }
  }
`
