/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./index.html", "./src/js/**/*.js"],
  theme: {
    extend: {
      colors: {
        gold: '#F5C518',
        'gold-dim': '#c9a012',
        background: '#080808',
        surface: '#101010',
        surface2: '#161616',
        borderline: 'rgba(255,255,255,0.07)',
        muted: '#666666',
      },
      fontFamily: {
        syne: ['Syne', 'sans-serif'],
        mono: ['DM Mono', 'monospace'],
      },
      cursor: {
        none: 'none',
      }
    },
  },
  plugins: [],
}