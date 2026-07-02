import { gql } from '@apollo/client'

export const AGREGAR_LIBRO = gql`
  mutation AgregarLibro($input: LibroInput!) {
    agregarLibro(input: $input) {
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

export const AGREGAR_AUTOR = gql`
  mutation AgregarAutor($input: AutorInput!) {
    agregarAutor(input: $input) {
      id
      nombre
      nacionalidad
    }
  }
`

export const AGREGAR_COLECCION = gql`
  mutation AgregarColeccion($input: ColeccionInput!) {
    agregarColeccion(input: $input) {
      id
      titulo
      anio
    }
  }
`

export const ELIMINAR_LIBRO = gql`
  mutation EliminarLibro($id: ID!) {
    eliminarLibro(id: $id)
  }
`
