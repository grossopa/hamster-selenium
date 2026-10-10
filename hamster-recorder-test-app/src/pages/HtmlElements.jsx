import { Typography, Box, Paper } from '@mui/material'

/**
 * Tests recorder with native HTML elements (non-MUI).
 * Covers HtmlSelect, HtmlTable detection and basic HTML form elements.
 */
export default function HtmlElements() {
  return (
    <>
      <Typography variant="h4" gutterBottom id="page-title" name="pageTitle">
        HTML Elements
      </Typography>
      <Typography variant="body1" paragraph>
        Native HTML elements for testing HtmlSelect, HtmlTable detection and basic form handling.
      </Typography>

      {/* ===== Native Form ===== */}
      <Paper sx={{ p: 2, mb: 3 }}>
        <Typography variant="h5" gutterBottom>Native Form</Typography>
        <form id="native-form" name="registrationForm" action="#" onSubmit={(e) => e.preventDefault()}>
          <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, maxWidth: 400 }}>
            <label>
              Full Name:
              <input id="html-input-name" name="fullName" type="text" placeholder="Enter name" />
            </label>
            <label>
              Email:
              <input id="html-input-email" name="email" type="email" placeholder="Enter email" />
            </label>
            <label>
              Password:
              <input id="html-input-password" name="password" type="password" placeholder="Enter password" />
            </label>
            <label>
              Age:
              <input id="html-input-age" name="age" type="number" min="1" max="120" />
            </label>
            <label>
              Birthday:
              <input id="html-input-birthday" name="birthday" type="date" />
            </label>
            <label>
              Favorite Color:
              <input id="html-input-color" name="favColor" type="text" list="color-options" />
              <datalist id="color-options">
                <option value="Red" />
                <option value="Blue" />
                <option value="Green" />
              </datalist>
            </label>

            <label>
              Bio:
              <textarea id="html-textarea" name="bio" rows={4} placeholder="Tell us about yourself" />
            </label>

            <Box>
              <label>
                <input id="html-checkbox-terms" name="acceptTerms" type="checkbox" /> I accept the terms
              </label>
            </Box>

            <Box>
              <Typography variant="subtitle2" gutterBottom>Subscription Type:</Typography>
              <label>
                <input type="radio" name="subscription" value="free" /> Free
              </label>
              <label>
                <input type="radio" name="subscription" value="basic" /> Basic
              </label>
              <label>
                <input type="radio" name="subscription" value="premium" /> Premium
              </label>
            </Box>

            <button id="html-submit-btn" type="submit">Submit</button>
            <button id="html-reset-btn" type="reset">Reset</button>
          </Box>
        </form>
      </Paper>

      {/* ===== Native Select ===== */}
      <Paper sx={{ p: 2, mb: 3 }}>
        <Typography variant="h5" gutterBottom>Native Select (HtmlSelect)</Typography>
        <Box sx={{ display: 'flex', gap: 3, flexWrap: 'wrap' }}>
          <Box>
            <label htmlFor="html-select-country">Country:</label>
            <select id="html-select-country" name="country">
              <option value="">Select a country</option>
              <option value="us">United States</option>
              <option value="uk">United Kingdom</option>
              <option value="cn">China</option>
              <option value="jp">Japan</option>
              <option value="de">Germany</option>
            </select>
          </Box>
          <Box>
            <label htmlFor="html-select-multi">Skills (multi-select):</label>
            <select id="html-select-multi" name="skills" multiple size={4}>
              <option value="java">Java</option>
              <option value="python">Python</option>
              <option value="javascript">JavaScript</option>
              <option value="go">Go</option>
              <option value="rust">Rust</option>
            </select>
          </Box>
        </Box>
      </Paper>

      {/* ===== Native Table ===== */}
      <Paper sx={{ p: 2, mb: 3 }}>
        <Typography variant="h5" gutterBottom>Native Table (HtmlTable)</Typography>
        <table id="html-table-products" name="productsTable" border="1" cellPadding="8" cellSpacing="0">
          <thead>
            <tr>
              <th>Product</th>
              <th>Price</th>
              <th>Quantity</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            <tr id="product-row-1">
              <td>Widget A</td>
              <td>$10.00</td>
              <td>100</td>
              <td>In Stock</td>
            </tr>
            <tr id="product-row-2">
              <td>Widget B</td>
              <td>$25.00</td>
              <td>50</td>
              <td>In Stock</td>
            </tr>
            <tr id="product-row-3">
              <td>Widget C</td>
              <td>$5.00</td>
              <td>0</td>
              <td>Out of Stock</td>
            </tr>
            <tr id="product-row-4">
              <td>Widget D</td>
              <td>$15.00</td>
              <td>200</td>
              <td>In Stock</td>
            </tr>
          </tbody>
        </table>
      </Paper>

      {/* ===== Native Links ===== */}
      <Paper sx={{ p: 2, mb: 3 }}>
        <Typography variant="h5" gutterBottom>Native Links</Typography>
        <Box sx={{ display: 'flex', gap: 2 }}>
          <a id="html-link-home" href="/" name="homeLink">Home</a>
          <a id="html-link-external" href="https://example.com" name="externalLink" target="_blank" rel="noopener">
            External Link
          </a>
          <a id="html-link-anchor" href="#html-table-products">Jump to Table</a>
        </Box>
      </Paper>
    </>
  )
}
