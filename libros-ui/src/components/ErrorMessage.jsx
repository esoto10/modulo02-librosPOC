export default function ErrorMessage({ message }) {
  return (
    <div className="rounded-lg bg-red-50 border border-red-200 p-4 flex gap-3 items-start">
      <span className="text-red-500 text-xl leading-none mt-0.5">⚠️</span>
      <div>
        <p className="font-semibold text-red-700">Error</p>
        <p className="text-red-600 text-sm mt-0.5">{message}</p>
      </div>
    </div>
  )
}
