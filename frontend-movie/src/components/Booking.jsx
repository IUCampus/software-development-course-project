import { useEffect, useMemo, useState } from 'react';
import axios from 'axios';
import {
  AppBar,
  Toolbar,
  Typography,
  Container,
  Box,
  Card,
  CardContent,
  CardActions,
  TextField,
  MenuItem,
  Button,
  Stack,
  Chip,
  Alert,
} from '@mui/material';
import { useLocation, useNavigate, useParams } from 'react-router-dom';

export default function Booking() {
  const { movieId } = useParams();
  const location = useLocation();
  const navigate = useNavigate();

  const [movie, setMovie] = useState(location.state?.movie || null);
  const [loadingMovie, setLoadingMovie] = useState(!location.state?.movie);
  const [movieError, setMovieError] = useState(null);

  const [showDate, setShowDate] = useState('');
  const [showTime, setShowTime] = useState('');
  const [tickets, setTickets] = useState(1);
  const [userId, setUserId] = useState('');
  const [seatNumber, setSeatNumber] = useState('');
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState(null);
  const [successMessage, setSuccessMessage] = useState('');

  const availableTimes = useMemo(
    () => ['10:00', '13:00', '16:00', '19:00', '22:00'],
    []
  );

  // Load movie if not provided via navigation state
  useEffect(() => {
    if (movie || !movieId) return;

    (async () => {
      try {
        setLoadingMovie(true);
        setMovieError(null);
        const res = await axios.get(`/api/movies/${movieId}`);
        setMovie(res.data);
      } catch (e) {
        setMovieError('Unable to load movie details.');
      } finally {
        setLoadingMovie(false);
      }
    })();
  }, [movie, movieId]);

  const handleBack = () => {
    navigate('/');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setSuccessMessage('');

    if (!userId || !seatNumber) {
      setError('User ID and Seat Number are required.');
      return;
    }
    if (!showDate || !showTime) {
      setError('Please select a date and time (for your reference).');
      return;
    }
    if (tickets < 1) {
      setError('Number of tickets must be at least 1.');
      return;
    }

    setSubmitting(true);
    try {
      await axios.post('/api/bookings', null, {
        params: {
          userId,
          movieId: movie.id,
          seatNumber,
        },
      });

      setSuccessMessage('Your booking has been created! Status: PENDING_PAYMENT.');
    } catch (err) {
      const msg =
        err?.response?.data?.message ||
        err?.response?.data ||
        'Unable to complete booking. The seat may already be booked.';
      setError(typeof msg === 'string' ? msg : 'Unable to complete booking.');
    } finally {
      setSubmitting(false);
    }
  };

  if (loadingMovie) {
    return (
      <>
        <AppBar position="sticky" color="primary" elevation={1}>
          <Toolbar>
            <Typography variant="h6" sx={{ flexGrow: 1 }}>
              Booking
            </Typography>
          </Toolbar>
        </AppBar>
        <Container maxWidth="sm" sx={{ py: 6 }}>
          <Typography>Loading movie details…</Typography>
        </Container>
      </>
    );
  }

  if (!movie || movieError) {
    return (
      <>
        <AppBar position="sticky" color="primary" elevation={1}>
          <Toolbar>
            <Typography variant="h6" sx={{ flexGrow: 1 }}>
              Booking
            </Typography>
          </Toolbar>
        </AppBar>
        <Container maxWidth="sm" sx={{ py: 6 }}>
          <Alert severity="error" sx={{ mb: 2 }}>
            {movieError || 'Movie not found.'}
          </Alert>
          <Button variant="contained" color="primary" onClick={handleBack}>
            Back to Movies
          </Button>
        </Container>
      </>
    );
  }

  return (
    <>
      <AppBar position="sticky" color="primary" elevation={1}>
        <Toolbar>
          <Typography variant="h6" sx={{ flexGrow: 1 }}>
            Booking for {movie.title}
          </Typography>
          <Button color="inherit" onClick={handleBack}>
            Back to Movies
          </Button>
        </Toolbar>
      </AppBar>

      <Container maxWidth="md" sx={{ py: 4 }}>
        <Card sx={{ maxWidth: 900, mx: 'auto' }}>
          <CardContent>
            <Stack
              direction={{ xs: 'column', md: 'row' }}
              spacing={3}
              sx={{ mb: 3 }}
            >
              <Box
                component="img"
                src={
                  movie.posterUrl ||
                  'https://via.placeholder.com/260x390?text=No+Poster'
                }
                alt={movie.title}
                sx={{
                  width: { xs: '100%', md: 260 },
                  height: { xs: 'auto', md: 390 },
                  objectFit: 'cover',
                  borderRadius: 2,
                }}
              />
              <Box sx={{ flex: 1 }}>
                <Typography variant="h5" sx={{ fontWeight: 600 }} gutterBottom>
                  {movie.title}
                </Typography>
                <Stack direction="row" spacing={1} sx={{ mb: 1, flexWrap: 'wrap' }}>
                  {movie.genre && (
                    <Chip label={movie.genre} color="secondary" variant="outlined" />
                  )}
                  {typeof movie.rating === 'number' && (
                    <Chip
                      label={`Rating: ${movie.rating.toFixed(1)}`}
                      color="primary"
                      variant="outlined"
                    />
                  )}
                </Stack>
                {movie.description && (
                  <Typography variant="body2" color="text.secondary">
                    {movie.description}
                  </Typography>
                )}
              </Box>
            </Stack>

            {error && (
              <Alert severity="error" sx={{ mb: 2 }}>
                {error}
              </Alert>
            )}
            {successMessage && (
              <Alert severity="success" sx={{ mb: 2 }}>
                {successMessage}
              </Alert>
            )}

            <Box component="form" noValidate onSubmit={handleSubmit}>
              <Stack spacing={2}>
                <Stack
                  direction={{ xs: 'column', sm: 'row' }}
                  spacing={2}
                  sx={{ alignItems: { xs: 'stretch', sm: 'flex-start' } }}
                >
                  <TextField
                    label="Show Date"
                    type="date"
                    value={showDate}
                    onChange={(e) => setShowDate(e.target.value)}
                    slotProps={{
                      inputLabel: { shrink: true }
                    }}
                    fullWidth
                    required
                  />
                  <TextField
                    label="Show Time"
                    select
                    value={showTime}
                    onChange={(e) => setShowTime(e.target.value)}
                    fullWidth
                    required
                  >
                    {availableTimes.map((time) => (
                      <MenuItem key={time} value={time}>
                        {time}
                      </MenuItem>
                    ))}
                  </TextField>
                  <TextField
                    label="Tickets"
                    type="number"
                    value={tickets}
                    onChange={(e) => setTickets(Number(e.target.value) || 1)}
                    slotProps={{
                      htmlInput: { min: 1 }
                    }}
                    sx={{ width: { xs: '100%', sm: 140 } }}
                    required
                  />
                </Stack>

                <Stack
                  direction={{ xs: 'column', sm: 'row' }}
                  spacing={2}
                  sx={{ alignItems: { xs: 'stretch', sm: 'flex-start' } }}
                >
                  <TextField
                    label="User ID"
                    value={userId}
                    onChange={(e) => setUserId(e.target.value)}
                    fullWidth
                    required
                    helperText="Enter your user ID (used by backend)."
                  />
                  <TextField
                    label="Seat Number"
                    value={seatNumber}
                    onChange={(e) => setSeatNumber(e.target.value.toUpperCase())}
                    fullWidth
                    required
                    helperText="e.g. A1, B5"
                  />
                </Stack>
              </Stack>

              <CardActions sx={{ mt: 3, px: 0, justifyContent: 'flex-end' }}>
                <Button
                  variant="outlined"
                  color="inherit"
                  onClick={handleBack}
                  disabled={submitting}
                >
                  Cancel
                </Button>
                <Button
                  type="submit"
                  variant="contained"
                  color="primary"
                  disabled={submitting}
                >
                  {submitting ? 'Booking…' : 'Confirm Booking'}
                </Button>
              </CardActions>
            </Box>
          </CardContent>
        </Card>
      </Container>
    </>
  );
}