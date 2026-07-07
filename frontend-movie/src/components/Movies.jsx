import { useEffect, useMemo, useState } from 'react';
import axios from 'axios';
import {
  AppBar,
  Toolbar,
  Typography,
  Container,
  Grid,
  Card,
  CardContent,
  CardMedia,
  CardActions,
  Button,
  Chip,
  TextField,
  Box,
  Skeleton,
  Alert,
  Stack,
} from '@mui/material';
import { useNavigate } from 'react-router-dom';

// Conceptual React Component for displaying movies
export default function MovieCatalog() {
  const [movies, setMovies] = useState([]);
  const [filtered, setFiltered] = useState([]);
  const [search, setSearch] = useState('');
  const [selectedGenre, setSelectedGenre] = useState('All');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  // Load movies
  useEffect(() => {
    let isMounted = true;

    async function fetchMovies() {
      setLoading(true);
      setError(null);

      try {
        const response = await axios.get('/api/movies/all');
        if (!isMounted) return;

        const data = response.data || [];
        setMovies(data);
      } catch (err) {
        if (!isMounted) return;
        setError('Unable to load movies right now. Please try again later.');
      } finally {
        if (isMounted) {
          setLoading(false);
        }
      }
    }

    fetchMovies();

    return () => {
      isMounted = false;
    };
  }, []);

  // Distinct genres for filters
  const genres = useMemo(() => {
    const unique = new Set();
    movies.forEach((m) => {
      if (m.genre) unique.add(m.genre);
    });
    return ['All', ...Array.from(unique).sort()];
  }, [movies]);

  // Filtered list
  useEffect(() => {
    let list = movies;

    if (selectedGenre !== 'All') {
      list = list.filter((m) => m.genre === selectedGenre);
    }

    if (search.trim()) {
      const q = search.trim().toLowerCase();
      list = list.filter(
        (m) =>
          m.title?.toLowerCase().includes(q) ||
          m.description?.toLowerCase().includes(q)
      );
    }

    setFiltered(list);
  }, [movies, search, selectedGenre]);

  const handleRetry = () => {
    setMovies([]);
    setFiltered([]);
    setSearch('');
    setSelectedGenre('All');
    setLoading(true);
    setError(null);

    (async () => {
      try {
        const response = await axios.get('/api/movies/all');
        const data = response.data || [];
        setMovies(data);
      } catch (err) {
        setError('Unable to load movies right now. Please try again later.');
      } finally {
        setLoading(false);
      }
    })();
  };

  const renderSkeletonGrid = () => (
    <Grid container spacing={4}>
      {Array.from({ length: 6 }).map((_, idx) => (
        <Grid item="true" xs={12} sm={6} md={4} key={idx}>
          <Card sx={{ height: '100%', display: 'flex', flexDirection: 'column' }}>
            <Stack direction="row" spacing={2}>
              <Skeleton
                variant="rectangular"
                sx={{ width: 120, height: 180, borderRadius: 1, m: 2 }}
              />
              <CardContent sx={{ flexGrow: 1 }}>
                <Skeleton width="60%" />
                <Skeleton width="40%" />
                <Skeleton width="90%" />
                <Skeleton width="80%" />
              </CardContent>
            </Stack>
            <CardActions sx={{ p: 2, pt: 0 }}>
              <Skeleton variant="rounded" width={120} height={36} />
            </CardActions>
          </Card>
        </Grid>
      ))}
    </Grid>
  );

  const renderEmptyState = () => (
    <Box
      sx={{
        mt: 8,
        textAlign: 'center',
        color: 'text.secondary',
      }}
    >
      <Typography variant="h6" gutterBottom>
        No movies found
      </Typography>
      <Typography variant="body2">
        Try changing the search text or selecting a different genre.
      </Typography>
    </Box>
  );

  return (
    <>
      <AppBar position="sticky" color="primary" elevation={1}>
        <Toolbar>
          <Typography
            variant="h6"
            sx={{ flexGrow: 1, fontWeight: 600, letterSpacing: 0.5 }}
          >
            Movie Catalog
          </Typography>
        </Toolbar>
      </AppBar>

      <Container maxWidth="lg" sx={{ py: 4 }}>
        {/* Search & Filters */}
        <Stack spacing={3} sx={{ mb: 4 }}>
          {error && (
            <Alert
              severity="error"
              action={
                <Button color="inherit" size="small" onClick={handleRetry}>
                  Retry
                </Button>
              }
            >
              {error}
            </Alert>
          )}

          <Stack
            spacing={2}
            direction={{ xs: 'column', sm: 'row' }}
            sx={{
              alignItems: { xs: 'stretch', sm: 'center' },
              justifyContent: 'space-between',
            }}
          >
            <TextField
              label="Search movies"
              placeholder="Search by title or description"
              variant="outlined"
              size="small"
              fullWidth
              value={search}
              onChange={(e) => setSearch(e.target.value)}
            />
            <Stack direction="row" spacing={1} sx={{ flexWrap: 'wrap' }}>
              {genres.map((genre) => (
                <Chip
                  key={genre}
                  label={genre}
                  variant={selectedGenre === genre ? 'filled' : 'outlined'}
                  color={selectedGenre === genre ? 'primary' : 'default'}
                  onClick={() => setSelectedGenre(genre)}
                  sx={{ mt: { xs: 1, sm: 0 } }}
                />
              ))}
            </Stack>
          </Stack>
        </Stack>

        {/* Movies grid */}
        {loading
          ? renderSkeletonGrid()
          : filtered.length === 0
          ? renderEmptyState()
          : (
          <Grid container spacing={4}>
            {filtered.map((movie) => (
              <Grid item="true" key={movie.id} xs={12} sm={6} md={4}>
                <Card
                  sx={{
                    height: '100%',
                    display: 'flex',
                    flexDirection: 'column',
                    borderRadius: 3,
                    boxShadow: 3,
                    '&:hover': {
                      boxShadow: 6,
                      transform: 'translateY(-4px)',
                      transition: 'all 0.2s ease-in-out',
                    },
                  }}
                >
                  <Stack
                    direction="row"
                    spacing={2}
                    sx={{ p: 2, pb: 1, alignItems: 'flex-start' }}
                  >
                    {/* Poster on the left */}
                    <CardMedia
                      component="img"
                      image={
                        movie.posterUrl ||
                        'https://via.placeholder.com/120x180?text=No+Poster'
                      }
                      alt={movie.title}
                      sx={{
                        width: 120,
                        height: 180,
                        objectFit: 'cover',
                        borderRadius: 2,
                        flexShrink: 0,
                        cursor: movie.posterUrl ? 'pointer' : 'default',
                      }}
                      onClick={() => {
                        if (movie.posterUrl) {
                          window.open(movie.posterUrl, '_blank');
                        }
                      }}
                    />

                    {/* Description on the right */}
                    <CardContent sx={{ flexGrow: 1, p: 0 }}>
                      <Typography
                        gutterBottom
                        variant="h6"
                        component="h2"
                        sx={{ fontWeight: 600 }}
                      >
                        {movie.title}
                      </Typography>
                      <Stack
                        direction="row"
                        spacing={1}
                        sx={{ alignItems: 'center', mb: 1 }}
                      >
                        {movie.genre && (
                          <Chip
                            size="small"
                            label={movie.genre}
                            color="secondary"
                            variant="outlined"
                          />
                        )}
                        {typeof movie.rating === 'number' && (
                          <Typography
                            variant="body2"
                            color="text.secondary"
                            sx={{ ml: 'auto' }}
                          >
                            Rating: <strong>{movie.rating.toFixed(1)}</strong>
                          </Typography>
                        )}
                      </Stack>
                      <Typography variant="body2" color="text.secondary">
                        {movie.description?.length > 150
                          ? `${movie.description.slice(0, 150)}...`
                          : movie.description}
                      </Typography>
                    </CardContent>
                  </Stack>

                  <CardActions sx={{ p: 2, pt: 0, mt: 'auto' }}>
                    <Button
                      fullWidth
                      variant="contained"
                      color="primary"
                      size="medium"
                      onClick={() => {
                        navigate(`/booking/${movie.id}`, { state: { movie } });
                      }}
                    >
                      Book Tickets
                    </Button>
                  </CardActions>
                </Card>
              </Grid>
            ))}
          </Grid>
            )}
      </Container>
    </>
  );
}