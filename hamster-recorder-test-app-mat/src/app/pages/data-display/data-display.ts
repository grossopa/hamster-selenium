import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatBadgeModule } from '@angular/material/badge';
import { MatChipsModule } from '@angular/material/chips';
import { MatListModule } from '@angular/material/list';
import { MatDividerModule } from '@angular/material/divider';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';

const tableRows = [
  { id: 1, name: 'Alice', email: 'alice@example.com', role: 'Admin' },
  { id: 2, name: 'Bob', email: 'bob@example.com', role: 'User' },
  { id: 3, name: 'Charlie', email: 'charlie@example.com', role: 'Editor' },
  { id: 4, name: 'Diana', email: 'diana@example.com', role: 'User' },
];

@Component({
  imports: [
    CommonModule,
    MatIconModule, MatBadgeModule, MatChipsModule,
    MatListModule, MatDividerModule, MatTableModule, MatCardModule,
  ],
  selector: 'app-data-display',
  templateUrl: './data-display.html',
})
export class DataDisplayComponent {
  protected readonly tableRows = tableRows;
  protected readonly displayedColumns = ['id', 'name', 'email', 'role'];
}
